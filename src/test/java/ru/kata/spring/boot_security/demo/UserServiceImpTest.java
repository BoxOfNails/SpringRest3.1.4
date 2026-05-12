package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.WebUserDto;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

	@Mock
	UserDao userDao;

	@Mock
	RoleService roleService;

	@InjectMocks
	@Spy
	UserServiceImp userService;

	@Spy
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void findAllUsersShouldNotChangeList(){
		List<User> listOfUsers = new ArrayList<>();
		listOfUsers.add(new User("Alex", "Lobe", "Lobe@gmail.com"));
		when(userDao.findAllUsers()).thenReturn(listOfUsers);

		List<User> result = userService.findAllUsers();

		assertThat(result).isEqualTo(listOfUsers);
	}

	@Test
	public void findAllUsersShouldReturnEmptyListIfNotExists() {
		List<User> listOfUsers = new ArrayList<>();
		when(userDao.findAllUsers()).thenReturn(listOfUsers);

		List<User> result = userService.findAllUsers();

		assertThat(result.size()).isEqualTo(0);
	}

	@Test
	public void findByIdShouldReturnCorrectUser() {
		User testUser = new User("Alex", "Lobe", "Lobe@gmail.com");
		testUser.setId(1);
		when(userDao.findById(1)).thenReturn(testUser);

		User result = userService.findById(1);

		assertThat(result).isEqualTo(testUser);
	}

	@Test
	public void findByIdShouldReturnNullIfNotFound() {
		when(userDao.findById(1)).thenReturn(null);

		User result = userService.findById(1);

		assertThat(result).isEqualTo(null);
	}

	@Test
	public void deleteByIdShouldInvokeDelete(){
		userService.deleteById(1);
		verify(userDao).deleteById(1);
	}

	@Test
	public void findByUserNameShouldReturnCorrectUser() {
		User testUser = new User("Alex", "password", true);
		when(userDao.findByUsername("Alex")).thenReturn(testUser);

		User result = userService.findByUserName("Alex");

		assertThat(result).isEqualTo(testUser);
	}

	@Test
	public void findByUserNameShouldReturnNullIfNotFound() {
		when(userDao.findByUsername("Alex")).thenReturn(null);

		User result = userService.findByUserName("Alex");

		assertThat(result).isEqualTo(null);
	}

	@Test
	public void saveShouldReturnUser() {
		User testUser = new User("Alex", "password", true);
		when(userDao.save(testUser)).thenReturn(testUser);

		User result = userService.save(testUser);

		assertThat(result).isEqualTo(testUser);
	}

	@Test
	public void saveShouldEncryptFormPassword() {
		User testUser = new User("Alex", "password", true);
		when(userDao.save(testUser)).thenReturn(testUser);

		User result = userService.save(testUser);

		assertThat(result.getPassword()).isNotEqualTo("password");
	}

	@Test
	public void saveShouldSetRoles() {
		User testUser = new User("Alex", "password", true);
		Set<Integer> roleIds = new HashSet<>();
		roleIds.add(1);
		roleIds.add(2);
		testUser.setRoleIds(roleIds);
		Set<Role> roles = new HashSet<>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));

		when(roleService.findByIds(testUser.getRoleIds())).thenReturn(roles);
		when(userDao.save(testUser)).thenReturn(testUser);

		User result = userService.save(testUser);

		assertThat(result.getRoles()).isEqualTo(roles);
	}

	@Test
	public void registerShouldMapUserFields() {
		WebUserDto webUserDto = new WebUserDto("username","password","firstName","lastName","email");
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		doReturn(new User()).when(userService).save(any(User.class));

		userService.register(webUserDto);

		verify(userService).save(userCaptor.capture());
		User capturedUser = userCaptor.getValue();
		assertThat(capturedUser.getUsername()).isEqualTo(webUserDto.getUsername());
		assertThat(capturedUser.getFormPassword()).isEqualTo(webUserDto.getPassword());
		assertThat(capturedUser.getFirstName()).isEqualTo(webUserDto.getFirstName());
		assertThat(capturedUser.getLastName()).isEqualTo(webUserDto.getLastName());
		assertThat(capturedUser.getEmail()).isEqualTo(webUserDto.getEmail());
		assertThat(capturedUser.getRoleIds()).isNotEqualTo(null);
	}



}
