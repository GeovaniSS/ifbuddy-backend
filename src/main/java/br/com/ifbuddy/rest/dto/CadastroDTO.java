package br.com.ifbuddy.rest.dto;

import lombok.Data;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@RegisterForReflection
public class CadastroDTO {
  @NotBlank(message = "Matrícula é obrigatória")
  private String matricula;

  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Email(message = "Email inválido")
  @Pattern(
    regexp = "^[a-zA-Z0-9._%+-]+@estudante\\.ifb\\.edu\\.br$", 
    message = "O e-mail deve ser institucional (ex: nome@estudante.ifb.edu.br)"
  )
  private String email;

  @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
  private String senha;
}
