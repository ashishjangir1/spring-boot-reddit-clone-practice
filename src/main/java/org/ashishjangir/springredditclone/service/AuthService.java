package org.ashishjangir.springredditclone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.ashishjangir.springredditclone.dto.RegisterRequest;
import org.ashishjangir.springredditclone.model.NotificationEmail;
import org.ashishjangir.springredditclone.model.User;
import org.ashishjangir.springredditclone.model.VerificationToken;
import org.ashishjangir.springredditclone.repository.UserRepository;
import org.ashishjangir.springredditclone.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;

	private VerificationTokenRepository verificationTokenRepository;
	
	private final MailService mailService;
	
	public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.mailService = mailService;
	}

	@Transactional
	public void signup(RegisterRequest registerRequest)
	{
		User user = new User();
		user.setUsername(registerRequest.getUserName());
		user.setEmail(registerRequest.getEmail());
		user.setUsername(registerRequest.getPassword());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);

		String token = generateVarificationToken(user);
		mailService.sendMail(new NotificationEmail("Please activate your account",user.getEmail(), "Thankyou for signing up" + "http://localhost:8080/api/auth/accountVerfication/"+token));
	}

	private String generateVarificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepository.save(verificationToken);
		return token;
	}
}
