package co.edu.usco.pw.loginSpringSecurityBasic;

import co.edu.usco.pw.loginSpringSecurityBasic.persistence.entity.RoleEntity;
import co.edu.usco.pw.loginSpringSecurityBasic.persistence.entity.RoleEnum;
import co.edu.usco.pw.loginSpringSecurityBasic.persistence.entity.UserEntity;
import co.edu.usco.pw.loginSpringSecurityBasic.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class LoginSpringSecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginSpringSecurityBasicApplication.class, args);
	}


	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepository) {
		return args -> {
			// Create roles
			RoleEntity roleUser = RoleEntity.builder()
					.name(RoleEnum.USER)
					.build();

			RoleEntity roleAdmin = RoleEntity.builder()
					.name(RoleEnum.ADMIN)
					.build();

			RoleEntity roleVisitor = RoleEntity.builder()
					.name(RoleEnum.VISITOR)
					.build();

			// Create users and assign roles
			UserEntity userPedro = UserEntity.builder()
					.username("pedro")
					.password("1234")
					.isEnabled(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity userLiang = UserEntity.builder()
					.username("liang")
					.password("1234")
					.isEnabled(true)
					.roles(Set.of(roleAdmin))
					.build();

			// Save users after roles
			userRepository.saveAll(List.of(userLiang, userPedro));

		};
	}

}
