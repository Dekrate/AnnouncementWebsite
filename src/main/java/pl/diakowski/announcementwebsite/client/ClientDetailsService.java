package pl.diakowski.announcementwebsite.client;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Class created to allow logging in from an external database
 */
@Service
public class ClientDetailsService implements UserDetailsService {
	private final ClientRepository clientRepository;

	public ClientDetailsService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client = clientRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return User.builder()
				.username(client.getUsername())
				.password(client.getPassword())
				.roles(client.getClientRoles().stream().map(ClientRole::getName).toArray(String[]::new))
				.build();
	}
}
