package telran.java41.security.service;

import java.time.LocalDate;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java41.accounting.dao.UserAccountRepository;
import telran.java41.accounting.model.UserAccount;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	
	UserAccountRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = repository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));		
		String[] roles = userAccount.getRoles().stream()
							.map(r -> "ROLE_" + r.toUpperCase())
							.toArray(String[]::new);
		boolean passwordNonExpired = userAccount.getPasswordExpDate().isAfter(LocalDate.now());
		return new UserProfile(username, userAccount.getPassword(), 
				AuthorityUtils.createAuthorityList(roles), passwordNonExpired);
	}

	
}
