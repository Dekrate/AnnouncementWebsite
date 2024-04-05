package pl.diakowski.announcementwebsite.token;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.client.Client;
import pl.diakowski.announcementwebsite.client.ClientRepository;
import pl.diakowski.announcementwebsite.client.exception.ClientNotFoundException;
import pl.diakowski.announcementwebsite.token.exception.TokenExpiredException;
import pl.diakowski.announcementwebsite.token.exception.TokenNotExistsException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
	private final TokenRepository tokenRepository;
	private final ClientRepository clientRepository;
	public TokenService(TokenRepository tokenRepository, ClientRepository clientRepository) {
		this.tokenRepository = tokenRepository;
		this.clientRepository = clientRepository;
	}



	@Transactional
	public void activateClient(String token) throws TokenNotExistsException, TokenExpiredException {
		Token tokenEntity = tokenRepository.findByToken(token).orElseThrow(() -> new TokenNotExistsException("Invalid token"));
		if (tokenEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
			throw new TokenExpiredException("Token expired");
		}
		Client client = tokenEntity.getClient();
		client.setActive(true);
		clientRepository.save(client);
	}

	@Transactional
	public String createToken(Long id) throws ClientNotFoundException {
		Token token = new Token();
		token.setExpirationTime(LocalDateTime.now().plusDays(1));
		token.setClient(clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Invalid client")));
		token.setToken(UUID.randomUUID().toString());
		tokenRepository.save(token);
		return token.getToken();
	}

	public ClientRepository getClientRepository() {
		return clientRepository;
	}
}
