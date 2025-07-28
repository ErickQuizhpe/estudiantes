package com.api.estudiantes.configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.estudiantes.entity.company.Company;
import com.api.estudiantes.entity.company.SocialMedia;
import com.api.estudiantes.entity.studen.Financiera;
import com.api.estudiantes.entity.studen.Materia;
import com.api.estudiantes.entity.studen.Nota;
import com.api.estudiantes.entity.studen.Studen;
import com.api.estudiantes.entity.user.RoleEntity;
import com.api.estudiantes.entity.user.RoleName;
import com.api.estudiantes.entity.user.UserEntity;
import com.api.estudiantes.repository.CompanyRepository;
import com.api.estudiantes.repository.FinancieraRepository;
import com.api.estudiantes.repository.MateriaRepository;
import com.api.estudiantes.repository.NotaRepository;
import com.api.estudiantes.repository.RoleRepository;
import com.api.estudiantes.repository.StudenRepository;
import com.api.estudiantes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CompanyRepository companyRepository;
  private final StudenRepository studenRepository;
  private final MateriaRepository materiaRepository;
  private final NotaRepository notaRepository;
  private final FinancieraRepository financieraRepository;
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

    // Crear datos de prueba: Usuario estudiante completo
    if (userRepository.findByUsername("estudiante1").isEmpty() && 
        userRepository.findByEmail("juan.perez@estudiantes.com").isEmpty()) {
      
      // 1. Crear usuario estudiante
      UserEntity userEstudiante = userRepository.save(UserEntity.builder()
          .firstName("Juan Carlos")
          .lastName("Pérez González")
          .username("estudiante1")
          .email("juan.perez@estudiantes.com")
          .password(passwordEncoder.encode("123456"))
          .enabled(true)
          .activo(true)
          .roles(Set.of(estudianteRole))
          .build());

      // 2. Crear estudiante
      Studen estudiante = studenRepository.save(Studen.builder()
          .matricula("EST2025001")
          .telefono("0987654321")
          .fechaNacimiento(LocalDate.of(2002, 5, 15))
          .carrera("Ingeniería en Sistemas")
          .semestre(4)
          .fechaIngreso(LocalDate.of(2023, 9, 1))
          .activo(true)
          .usuario(userEstudiante)
          .build());

      // 3. Crear materias
      Materia matematicas = materiaRepository.save(Materia.builder()
          .codigo("MAT401")
          .nombre("Matemáticas Discretas")
          .descripcion("Fundamentos de matemáticas para sistemas")
          .creditos(4)
          .semestre(4)
          .carrera("Ingeniería en Sistemas")
          .activa(true)
          .build());

      Materia programacion = materiaRepository.save(Materia.builder()
          .codigo("PRG401")
          .nombre("Programación Orientada a Objetos")
          .descripcion("Conceptos avanzados de POO")
          .creditos(5)
          .semestre(4)
          .carrera("Ingeniería en Sistemas")
          .activa(true)
          .build());

      Materia baseDatos = materiaRepository.save(Materia.builder()
          .codigo("BDD401")
          .nombre("Base de Datos I")
          .descripcion("Fundamentos de bases de datos relacionales")
          .creditos(4)
          .semestre(4)
          .carrera("Ingeniería en Sistemas")
          .activa(true)
          .build());

      // 4. Crear notas
      // Notas de Matemáticas
      notaRepository.save(Nota.builder()
          .calificacion(85.0)
          .notaMaxima(100.0)
          .porcentaje(30.0)
          .tipoEvaluacion("Primer Parcial")
          .fechaEvaluacion(LocalDate.of(2025, 3, 15))
          .observaciones("Excelente comprensión de los conceptos")
          .activa(true)
          .estudiante(estudiante)
          .materia(matematicas)
          .build());

      notaRepository.save(Nota.builder()
          .calificacion(78.0)
          .notaMaxima(100.0)
          .porcentaje(30.0)
          .tipoEvaluacion("Segundo Parcial")
          .fechaEvaluacion(LocalDate.of(2025, 5, 10))
          .observaciones("Buen desempeño general")
          .activa(true)
          .estudiante(estudiante)
          .materia(matematicas)
          .build());

      // Notas de Programación
      notaRepository.save(Nota.builder()
          .calificacion(92.0)
          .notaMaxima(100.0)
          .porcentaje(25.0)
          .tipoEvaluacion("Proyecto Final")
          .fechaEvaluacion(LocalDate.of(2025, 6, 20))
          .observaciones("Excelente implementación del proyecto")
          .activa(true)
          .estudiante(estudiante)
          .materia(programacion)
          .build());

      notaRepository.save(Nota.builder()
          .calificacion(88.0)
          .notaMaxima(100.0)
          .porcentaje(20.0)
          .tipoEvaluacion("Quiz")
          .fechaEvaluacion(LocalDate.of(2025, 4, 8))
          .observaciones("Dominio de conceptos POO")
          .activa(true)
          .estudiante(estudiante)
          .materia(programacion)
          .build());

      // Notas de Base de Datos
      notaRepository.save(Nota.builder()
          .calificacion(82.0)
          .notaMaxima(100.0)
          .porcentaje(35.0)
          .tipoEvaluacion("Examen Final")
          .fechaEvaluacion(LocalDate.of(2025, 7, 15))
          .observaciones("Buena comprensión de SQL")
          .activa(true)
          .estudiante(estudiante)
          .materia(baseDatos)
          .build());

      // 5. Crear información financiera
      financieraRepository.save(Financiera.builder()
          .pensionMensual(new BigDecimal("250.00"))
          .descuento(new BigDecimal("25.00"))
          .montoPendiente(new BigDecimal("150.00"))
          .ultimoPago(LocalDate.of(2025, 6, 15))
          .fechaVencimiento(LocalDate.of(2025, 8, 15))
          .alDia(false) // Tiene monto pendiente
          .becado(true)
          .porcentajeBeca(15) // 15% de beca académica
          .observaciones("Beca por excelencia académica. Pendiente pago de julio.")
          .estudiante(estudiante)
          .build());

      System.out.println("✅ Datos de prueba creados exitosamente:");
      System.out.println("👤 Usuario: estudiante1 / 123456");
      System.out.println("🎓 Estudiante: Juan Carlos Pérez González (EST2025001)");
      System.out.println("📚 Materias: 3 materias de 4to semestre");
      System.out.println("📊 Notas: 5 calificaciones registradas");
      System.out.println("💰 Financiera: Pensión $250, Beca 15%, Pendiente $150");
    }

    // Crear segundo estudiante (diferentes datos para pruebas)
    if (userRepository.findByUsername("estudiante2").isEmpty() && 
        userRepository.findByEmail("maria.garcia@estudiantes.com").isEmpty()) {
      
      // Usuario estudiante 2
      UserEntity userEstudiante2 = userRepository.save(UserEntity.builder()
          .firstName("María José")
          .lastName("García Torres")
          .username("estudiante2")
          .email("maria.garcia@estudiantes.com")
          .password(passwordEncoder.encode("123456"))
          .enabled(true)
          .activo(true)
          .roles(Set.of(estudianteRole))
          .build());

      // Estudiante 2
      Studen estudiante2 = studenRepository.save(Studen.builder()
          .matricula("EST2025002")
          .telefono("0987654322")
          .fechaNacimiento(LocalDate.of(2003, 8, 22))
          .carrera("Ingeniería en Sistemas")
          .semestre(2)
          .fechaIngreso(LocalDate.of(2024, 9, 1))
          .activo(true)
          .usuario(userEstudiante2)
          .build());

      // Materias de 2do semestre
      Materia algebra = materiaRepository.save(Materia.builder()
          .codigo("ALG201")
          .nombre("Álgebra Lineal")
          .descripcion("Matemáticas aplicadas a sistemas")
          .creditos(4)
          .semestre(2)
          .carrera("Ingeniería en Sistemas")
          .activa(true)
          .build());

      Materia programacionBasica = materiaRepository.save(Materia.builder()
          .codigo("PRG201")
          .nombre("Programación I")
          .descripcion("Fundamentos de programación")
          .creditos(5)
          .semestre(2)
          .carrera("Ingeniería en Sistemas")
          .activa(true)
          .build());

      // Notas del estudiante 2
      notaRepository.save(Nota.builder()
          .calificacion(95.0)
          .notaMaxima(100.0)
          .porcentaje(40.0)
          .tipoEvaluacion("Examen Final")
          .fechaEvaluacion(LocalDate.of(2025, 7, 10))
          .observaciones("Excelente estudiante")
          .activa(true)
          .estudiante(estudiante2)
          .materia(algebra)
          .build());

      notaRepository.save(Nota.builder()
          .calificacion(90.0)
          .notaMaxima(100.0)
          .porcentaje(35.0)
          .tipoEvaluacion("Proyecto")
          .fechaEvaluacion(LocalDate.of(2025, 6, 25))
          .observaciones("Creatividad en la solución")
          .activa(true)
          .estudiante(estudiante2)
          .materia(programacionBasica)
          .build());

      // Información financiera 2 (estudiante al día)
      financieraRepository.save(Financiera.builder()
          .pensionMensual(new BigDecimal("220.00"))
          .descuento(new BigDecimal("0.00"))
          .montoPendiente(new BigDecimal("0.00"))
          .ultimoPago(LocalDate.of(2025, 7, 1))
          .fechaVencimiento(LocalDate.of(2025, 8, 1))
          .alDia(true)
          .becado(true)
          .porcentajeBeca(25) // 25% de beca deportiva
          .observaciones("Beca deportiva por representar a la institución en atletismo")
          .estudiante(estudiante2)
          .build());

      System.out.println("✅ Segundo estudiante creado:");
      System.out.println("👤 Usuario: estudiante2 / 123456");
      System.out.println("🎓 Estudiante: María José García Torres (EST2025002)");
      System.out.println("📚 Materias: 2 materias de 2do semestre");
      System.out.println("📊 Notas: 2 calificaciones excelentes");
      System.out.println("💰 Financiera: Al día, Beca deportiva 25%");
    }
  }
}
