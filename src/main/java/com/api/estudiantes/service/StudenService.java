package com.api.estudiantes.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.controller.dto.studen.StudenRequest;
import com.api.estudiantes.controller.dto.studen.StudenResponse;
import com.api.estudiantes.entity.studen.Studen;
import com.api.estudiantes.entity.user.RoleName;
import com.api.estudiantes.entity.user.UserEntity;
import com.api.estudiantes.repository.RoleRepository;
import com.api.estudiantes.repository.StudenRepository;
import com.api.estudiantes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudenService {

    private final StudenRepository studenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StudenResponse createStuden(StudenRequest request) {
        // Verificar que la matrícula no exista
        if (studenRepository.existsByMatricula(request.matricula())) {
            throw new RuntimeException("La matrícula ya existe");
        }

        // Verificar que el usuario no exista
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear usuario
        UserEntity user = UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password() != null ? request.password() : "123456"))
                .enabled(true)
                .activo(true)
                .roles(Set.of(roleRepository.findByName(RoleName.ESTUDIANTE)
                        .orElseThrow(() -> new RuntimeException("Rol ESTUDIANTE no encontrado"))))
                .build();

        UserEntity savedUser = userRepository.save(user);

        // Crear estudiante
        Studen studen = Studen.builder()
                .matricula(request.matricula())
                .telefono(request.telefono())
                .fechaNacimiento(request.fechaNacimiento())
                .carrera(request.carrera())
                .semestre(request.semestre())
                .fechaIngreso(request.fechaIngreso())
                .activo(request.activo() != null ? request.activo() : true)
                .usuario(savedUser)
                .build();

        Studen savedStuden = studenRepository.save(studen);
        return mapToStudenResponse(savedStuden);
    }

    public StudenResponse getStudenById(Long id) {
        Studen studen = studenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return mapToStudenResponse(studen);
    }

    public StudenResponse getStudenByMatricula(String matricula) {
        Studen studen = studenRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return mapToStudenResponse(studen);
    }

    public StudenResponse getStudenByUsuarioId(Long usuarioId) {
        Studen studen = studenRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return mapToStudenResponse(studen);
    }

    public List<StudenResponse> getAllStudens() {
        return studenRepository.findAll().stream()
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public List<StudenResponse> getAllStudensConFiltros(Boolean activo, Boolean usuarioActivo) {
        List<Studen> estudiantes = studenRepository.findAll();
        
        return estudiantes.stream()
                .filter(studen -> {
                    // Filtrar por estado del estudiante
                    if (activo != null && !activo.equals(studen.getActivo())) {
                        return false;
                    }
                    // Filtrar por estado del usuario
                    if (usuarioActivo != null && studen.getUsuario() != null 
                        && !usuarioActivo.equals(studen.getUsuario().getActivo())) {
                        return false;
                    }
                    return true;
                })
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public List<StudenResponse> getStudensActivos() {
        return studenRepository.findByActivoTrue().stream()
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public List<StudenResponse> getStudensInactivos() {
        return studenRepository.findByActivoFalse().stream()
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public List<StudenResponse> getStudensByCarrera(String carrera) {
        return studenRepository.findByCarreraAndActivoTrue(carrera).stream()
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public List<StudenResponse> getStudensByCarreraAndSemestre(String carrera, Integer semestre) {
        return studenRepository.findByCarreraAndSemestre(carrera, semestre).stream()
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public List<StudenResponse> searchStudens(String nombre) {
        return studenRepository.findByNombreOrMatriculaContaining(nombre).stream()
                .map(this::mapToStudenResponse)
                .collect(Collectors.toList());
    }

    public StudenResponse updateStuden(Long id, StudenRequest request) {
        Studen studen = studenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        UserEntity user = studen.getUsuario();

        // Verificar duplicados si se cambian los valores únicos
        if (!studen.getMatricula().equals(request.matricula()) && 
            studenRepository.existsByMatricula(request.matricula())) {
            throw new RuntimeException("La matrícula ya existe");
        }

        if (!user.getUsername().equals(request.username()) && 
            userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (!user.getEmail().equals(request.email()) && 
            userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Actualizar usuario
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setEmail(request.email());
        
        if (request.password() != null && !request.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        // Actualizar estudiante
        studen.setMatricula(request.matricula());
        studen.setTelefono(request.telefono());
        studen.setFechaNacimiento(request.fechaNacimiento());
        studen.setCarrera(request.carrera());
        studen.setSemestre(request.semestre());
        studen.setFechaIngreso(request.fechaIngreso());
        if (request.activo() != null) {
            studen.setActivo(request.activo());
        }

        userRepository.save(user);
        Studen updatedStuden = studenRepository.save(studen);
        return mapToStudenResponse(updatedStuden);
    }

    public void deleteStuden(Long id) {
        Studen studen = studenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        // Eliminar usuario asociado también
        UserEntity user = studen.getUsuario();
        studenRepository.delete(studen);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public StudenResponse activateStuden(Long id) {
        Studen studen = studenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        studen.setActivo(true);
        if (studen.getUsuario() != null) {
            studen.getUsuario().setActivo(true);
            userRepository.save(studen.getUsuario());
        }
        
        Studen updatedStuden = studenRepository.save(studen);
        return mapToStudenResponse(updatedStuden);
    }

    public StudenResponse deactivateStuden(Long id) {
        Studen studen = studenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        studen.setActivo(false);
        if (studen.getUsuario() != null) {
            studen.getUsuario().setActivo(false);
            userRepository.save(studen.getUsuario());
        }
        
        Studen updatedStuden = studenRepository.save(studen);
        return mapToStudenResponse(updatedStuden);
    }

    private StudenResponse mapToStudenResponse(Studen studen) {
        UserEntity user = studen.getUsuario();
        return new StudenResponse(
            studen.getId(),
            studen.getMatricula(),
            studen.getTelefono(),
            studen.getFechaNacimiento(),
            studen.getCarrera(),
            studen.getSemestre(),
            studen.getFechaIngreso(),
            studen.getActivo(),
            user != null ? user.getId() : null,
            user != null ? user.getFirstName() : null,
            user != null ? user.getLastName() : null,
            user != null ? user.getUsername() : null,
            user != null ? user.getEmail() : null,
            user != null ? user.getFechaCreacion() : null,
            user != null ? user.getUltimoAcceso() : null,
            studen.getNombreCompleto(),
            studen.getInformacionFinanciera() != null
        );
    }
}
