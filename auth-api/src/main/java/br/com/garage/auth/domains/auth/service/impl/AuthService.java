package br.com.garage.auth.domains.auth.service.impl;

import br.com.garage.auth.config.security.TokenService;
import br.com.garage.auth.domains.auth.gateway.IAuthGateway;
import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.domains.auth.service.IAuthService;
import br.com.garage.auth.exceptions.BusinessException;
import br.com.garage.auth.exceptions.NotFoundException;
import br.com.garage.auth.infraestructure.api.auth.dtos.RequestRefreshPasswordDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.TokenDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UserLoginRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthService implements IAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private static final String EMPRESA_ESTA_NULO = "O campo empresa esta nulo";
    private static final Exception TOKEN_INVALIDO = null;

    @Autowired
    private IAuthGateway authGateway;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public TokenDto auth(UserLoginRequestDto dto) {

        var login = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authenticate = authenticationManager.authenticate(login);
        String token = tokenService.buildToken(authenticate);
        Usuario usuario = authGateway.buscaUsuarioPorEmail(dto.email());
        TokenDto tokenDto = new TokenDto(token, "Bearer", null);
        if (usuario.getTenant() != null) {
            tokenDto.atualizaNomeEmpresa(usuario.getTenant().getNome());
        }
        usuario.atualizaDataUltimoLogin();
        authGateway.salvarUsuario(usuario);
        return tokenDto;
    }

    @Override
    public void updatePasswordByRefreshToken(RequestRefreshPasswordDto dto) throws BusinessException {
        var entity = authGateway.buscaUsuarioPorEmail(dto.email());

        if (!entity.getTokenRefreshPassword().equals(dto.tokenRefreshPassword())
                || !entity.isTokenRefreshPasswordValid()) {
            throw new BusinessException(TOKEN_INVALIDO);
        }
        validPasswordPolicies(dto.newPassword());
        entity.alteraPassword(passwordEncoder.encode(dto.newPassword()));
        authGateway.salvarUsuario(entity);
    }

    @Override
    public Usuario getUSerLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserDetails) authentication.getPrincipal();

        return authGateway.buscaUsuarioPorEmail(principal.getUsername());
    }

    @Override
    public Tenant getCompanyByUserLogged() throws NotFoundException {
        if (getUSerLogged().getTenant() == null) {
            throw new IllegalArgumentException(EMPRESA_ESTA_NULO);
        }

        return authGateway.buscarTenantPorId(getUSerLogged().getTenant().getId());
    }

    @Override
    public void validPasswordPolicies(String password) throws BusinessException {

        Matcher hasLetter = Pattern.compile("[a-z]").matcher(password);
        Matcher hasDigit = Pattern.compile("\\d").matcher(password);

        if (password.length() < 8 || !hasLetter.find() || !hasDigit.find()) {
            throw new BusinessException("password precisa ser mais complexo");
        }
    }

    @Override
    public void solicitaAtualizarPassword(String email) {
        Usuario usuario = authGateway.buscaUsuarioPorEmail(email);
        createRefreshToken(usuario);
        enviaEmailRefreshToken(usuario);
        authGateway.salvarUsuario(usuario);
    }

    private void enviaEmailRefreshToken(Usuario usuario) {

        String str = "http://localhost:4200/auth/update-password" +
                ";email=" +
                usuario.getEmail() +
                ";refreshtoken=" +
                usuario.getTokenRefreshPassword();

        logger.info("e-mail enviado com sucesso: {}", str);
    }

    private void createRefreshToken(Usuario usuario) {
        String token = passwordEncoder.encode(usuario.getEmail() + usuario.getPassword() + LocalDateTime.now());
        usuario.ativaRefreshToken(token);
        authGateway.salvarUsuario(usuario);
    }
}
