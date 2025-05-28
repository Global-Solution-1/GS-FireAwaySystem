package fireaway.com.service;


import fireaway.com.domainmodel.Usuario;
import fireaway.com.dto.LoginRequest;
import fireaway.com.repository.UsuarioRepository;
import fireaway.com.security.JwtTokenUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String autenticar(LoginRequest loginRequest) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            throw new Exception("Senha incorreta");
        }

        return jwtTokenUtil.generateToken(usuario.getEmail(), usuario.getPerfil());
    }
}
