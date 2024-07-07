package kr.co.polycube.backendtest.Service;

import kr.co.polycube.backendtest.DTO.UserDTO;
import kr.co.polycube.backendtest.Entity.User;

public interface UserService{
	
	User saveUser(User user);
	
	User getUserById(Long id);

	User updateUser(Long id, UserDTO userDTO);
	
}
