package fireaway.com.service;


import fireaway.com.domainmodel.Usuario;
import fireaway.com.domainmodel.enuns.PerfilUsuario;
import fireaway.com.dto.UsuarioMoradorDto;
import fireaway.com.dto.UsuarioRequestDto;
import fireaway.com.dto.UsuarioResponseDto;
import fireaway.com.exceptions.ResourceNotFoundException;
import fireaway.com.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UsuarioResponseDto saveUsuario(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPerfil(dto.getPerfil());
        usuario.setTelefone(dto.getTelefone());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        return new UsuarioResponseDto(savedUsuario);
    }


    public void saveMorador(UsuarioMoradorDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPerfil(PerfilUsuario.MORADOR);
        usuario.setEndereco(dto.getEndereco());
        usuario.setTelefone(dto.getTelefone());

        usuarioRepository.save(usuario);
    }



    public List<UsuarioResponseDto> listAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(UsuarioResponseDto::new)
                .toList();
    }

    public Page<UsuarioResponseDto> listAllPaged(Pageable pageable){
        return usuarioRepository.findAll(pageable).map(UsuarioResponseDto::new);
    }

    public UsuarioResponseDto findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        return new UsuarioResponseDto(usuario);
    }


    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario", id));
        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDto updateMorador(Long id, UsuarioMoradorDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setEndereco(dto.getEndereco());
        usuario.setTelefone(dto.getTelefone());

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDto(salvo);
    }


    public UsuarioResponseDto updateUsuario(Long id, UsuarioRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setTelefone(dto.getTelefone());
        usuario.setPerfil(dto.getPerfil());

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDto(salvo);
    }



}
