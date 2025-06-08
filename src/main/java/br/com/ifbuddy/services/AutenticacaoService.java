package br.com.ifbuddy.services;

import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;

import br.com.ifbuddy.exception.BusinessException;
import br.com.ifbuddy.models.Estudante;
import br.com.ifbuddy.repository.EstudanteRepository;
import br.com.ifbuddy.rest.dto.CadastroDTO;
import br.com.ifbuddy.rest.dto.LoginDTO;
import br.com.ifbuddy.rest.dto.TokenDTO;
import br.com.ifbuddy.rest.dto.UsuarioDTO;
import br.com.ifbuddy.utils.JwtUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

@RequestScoped
public class AutenticacaoService {
  @Inject
  EstudanteRepository estudanteRepository;

  @Inject
  PasswordService passwordService;

  @Inject
  TokenService tokenService;

  @Context
  HttpHeaders headers;

  @Transactional
  public Estudante cadastro(CadastroDTO cadastroDTO) {
    if (estudanteRepository.findByMatricula(cadastroDTO.getMatricula()).isPresent()) {
      throw new BusinessException("Matrícula já existe");
    }

    if (estudanteRepository.findByEmail(cadastroDTO.getEmail()).isPresent()) {
      throw new BusinessException("Email já existe");
    }

    Estudante estudante = new Estudante();
    String senhaHash = passwordService.encodePassword(cadastroDTO.getSenha());

    estudante.setMatricula(cadastroDTO.getMatricula());
    estudante.setNome(cadastroDTO.getNome());
    estudante.setEmail(cadastroDTO.getEmail());
    estudante.setSenhaHash(senhaHash);
    estudante.setAtivo(false);

    estudanteRepository.persist(estudante);

    return estudante;
  }

  @Transactional
  public TokenDTO login(LoginDTO loginDTO) {
    Estudante estudante = estudanteRepository.findByEmail(loginDTO.getEmail())
        .orElseThrow(() -> new BusinessException("Email ou senha inválidos"));
    boolean isValid = passwordService.verifyPassword(loginDTO.getSenha(), estudante.getSenhaHash());

    if (!isValid) {
      throw new BusinessException("Email ou senha inválidos");
    }

    String token = tokenService.generateUserToken(estudante.getEmail(), estudante.getMatricula());

    return new TokenDTO(
        estudante.getEstudanteId(),
        token);
  }

  @Transactional
  public UsuarioDTO validarToken() {
    String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new BusinessException("Token de autenticação ausente ou inválido");
    }

    String token = JwtUtils.getBearerToken(authHeader);
    JwtClaims claims;
    try {
      claims = tokenService.validateToken(token);
    } catch (Exception e) {
      throw new BusinessException("Token inválido ou expirado");
    }

    String email;

    try {
      email = claims.getClaimValue(Claims.sub.name(), String.class);
    } catch (Exception e) {
      throw new BusinessException("Token não contém email do usuário");
    }

    Estudante estudante = estudanteRepository.findByEmail(email)
        .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

    return new UsuarioDTO(
        estudante.getEstudanteId(),
        estudante.getNome(),
        estudante.getEmail(),
        estudante.getMatricula(),
        estudante.getFoto(),
        estudante.getAtivo());
  }

  public UsuarioDTO consultarUsuario(Long id) {
    Estudante estudante = estudanteRepository.findByIdOptional(id)
        .orElseThrow(() -> new BusinessException("Usuário não encontrado para o ID: " + id));

    return new UsuarioDTO(
        estudante.getEstudanteId(),
        estudante.getNome(),
        estudante.getEmail(),
        estudante.getMatricula(),
        estudante.getFoto(),
        estudante.getAtivo());
  }
}
