package br.com.garage.auth.domains.auth.service;

import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.exceptions.BusinessException;
import br.com.garage.auth.exceptions.NotFoundException;
import br.com.garage.auth.infraestructure.api.auth.dtos.RequestRefreshPasswordDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.TokenDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UserLoginRequestDto;

public interface IAuthService {

	TokenDto auth(UserLoginRequestDto dto);

	void updatePasswordByRefreshToken(RequestRefreshPasswordDto dto) throws BusinessException;

	Usuario getUSerLogged();

	Tenant getCompanyByUserLogged() throws NotFoundException;

	void validPasswordPolicies(String password);

	void solicitaAtualizarPassword(String login);
}
