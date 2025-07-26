package com.api.estudiantes.configuration;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.estudiantes.entity.company.Company;
import com.api.estudiantes.entity.company.SocialMedia;
import com.api.estudiantes.entity.user.RoleEntity;
import com.api.estudiantes.entity.user.RoleName;
import com.api.estudiantes.entity.user.UserEntity;
import com.api.estudiantes.repository.CompanyRepository;
import com.api.estudiantes.repository.RoleRepository;
import com.api.estudiantes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CompanyRepository companyRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {

    // Crear roles
    RoleEntity adminRole = roleRepository.findByName(RoleName.ADMIN)
        .orElseGet(() -> {
          RoleEntity role = new RoleEntity();
          role.setName(RoleName.ADMIN);
          role.setDescription("Administrador del sistema");
          return roleRepository.save(role);
        });
        
    RoleEntity estudianteRole = roleRepository.findByName(RoleName.ESTUDIANTE)
        .orElseGet(() -> {
          RoleEntity role = new RoleEntity();
          role.setName(RoleName.ESTUDIANTE);
          role.setDescription("Estudiante del sistema");
          return roleRepository.save(role);
        });

    // Crear usuario administrador por defecto
    if (userRepository.findByUsername("admin").isEmpty()) {
      userRepository.save(UserEntity.builder()
          .firstName("Administrador")
          .lastName("Sistema")
          .username("admin")
          .email("admin@estudiantes.com")
          .password(passwordEncoder.encode("admin"))
          .enabled(true)
          .roles(Set.of(adminRole))
          .build());
    }

    // Crear información de la institución educativa
    if (companyRepository.findAll().isEmpty()) {
      companyRepository.save(Company.builder()
          .name("Instituto Tecnológico Superior")
          .description("Sistema de Gestión de Estudiantes")
          .logoUrl("https://www.instituto.edu.ec/logo.png")
          .phone("07-123456789")
          .email("info@instituto.edu.ec")
          .address("Av. Principal 123, Cuenca, Ecuador")
          .city("Cuenca")
          .footerText("© 2025 Instituto Tecnológico Superior - Todos los derechos reservados")
          .mission(
              "Formar profesionales competentes e íntegros que contribuyan al desarrollo tecnológico y social del país.")
          .vision("Ser una institución educativa líder en formación tecnológica superior en Ecuador.")
          .socialMedia(Set.of(
              SocialMedia.builder()
                  .platform("Facebook")
                  .url("https://www.facebook.com/instituto")
                  .iconUrl("https://www.facebook.com/favicon.ico")
                  .build(),
              SocialMedia.builder()
                  .platform("Instagram")
                  .url("https://www.instagram.com/instituto")
                  .iconUrl("https://www.instagram.com/favicon.ico")
                  .build()))
          .build());
    }
  }
}
