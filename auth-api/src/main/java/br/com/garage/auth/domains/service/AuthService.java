package br.com.garage.auth.domains.service;

import br.com.garage.auth.config.security.TokenService;
import br.com.garage.auth.domains.gateway.IAuthGateway;
import br.com.garage.auth.domains.models.Tenant;
import br.com.garage.auth.domains.models.Usuario;
import br.com.garage.auth.domains.enums.EnumStatus;
import br.com.garage.auth.exceptions.BusinessException;
import br.com.garage.auth.exceptions.NotFoundException;
import br.com.garage.auth.infraestructure.rest.dtos.RequestRefreshPasswordDto;
import br.com.garage.auth.infraestructure.rest.dtos.TokenDto;
import br.com.garage.auth.infraestructure.rest.dtos.UserLoginRequestDto;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class AuthService {

    private static final String EMPRESA_ESTA_NULO = "O campo empresa esta nulo";
    private static final Exception TOKEN_INVALIDO = null;

    private final IAuthGateway authGateway;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IAuthGateway authGateway,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService,
                       PasswordEncoder passwordEncoder) {
        this.authGateway = authGateway;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenDto auth(UserLoginRequestDto dto) {

        var login = new UsernamePasswordAuthenticationToken(dto.email, dto.password);
        var authenticate = authenticationManager.authenticate(login);
        String token = tokenService.buildToken(authenticate);
        Usuario usuario = authGateway.buscaUsuarioPorEmail(dto.email);
        TokenDto tokenDto = new TokenDto(token, "Bearer", null);
        if (usuario.getTenant() != null) {
            if (usuario.getTenant().getStatus().equals(EnumStatus.INATIVO)) {
                throw new BusinessException("Tenant está inativa!");
            }

            tokenDto.tenantName = usuario.getTenant().getNome();
        }
        usuario.atualizaDataUltimoLogin();
        authGateway.salvarUsuario(usuario);
        return tokenDto;
    }

    public void updatePasswordByRefreshToken(RequestRefreshPasswordDto dto) throws BusinessException {
        var entity = authGateway.buscaUsuarioPorEmail(dto.getEmail());

        if (!entity.getTokenRefreshPassword().equals(dto.getTokenRefreshPassword())
                || !entity.isTokenRefreshPasswordValid()) {
            throw new BusinessException(TOKEN_INVALIDO);
        }
        validPasswordPolicies(dto.getNewPassword());
        entity.alteraPassword(passwordEncoder.encode(dto.getNewPassword()));
        authGateway.salvarUsuario(entity);
    }

    public Usuario getUSerLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserDetails) authentication.getPrincipal();

        return authGateway.buscaUsuarioPorEmail(principal.getUsername());
    }

    public Tenant getCompanyByUserLogged() throws NotFoundException {
        if (getUSerLogged().getTenant() == null) {
            throw new IllegalArgumentException(EMPRESA_ESTA_NULO);
        }

        return authGateway.buscarTenantPorId(getUSerLogged().getTenant().getId());
    }

    public void validPasswordPolicies(String password) throws BusinessException {

        Matcher hasLetter = Pattern.compile("[a-z]").matcher(password);
        Matcher hasDigit = Pattern.compile("\\d").matcher(password);

        if (password.length() < 8 || !hasLetter.find() || !hasDigit.find()) {
            throw new BusinessException("password precisa ser mais complexo");
        }
    }

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
        log.info("e-mail enviado com sucesso: {}", str);
    }

    private void createRefreshToken(Usuario usuario) {
        String token = passwordEncoder.encode(usuario.getEmail() + usuario.getPassword() + LocalDateTime.now());
        usuario.ativaRefreshToken(token);
        authGateway.salvarUsuario(usuario);
    }
}
