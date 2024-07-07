package kr.co.polycube.backendtest.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.DTO.UserDTO;
import kr.co.polycube.backendtest.Entity.User;
import kr.co.polycube.backendtest.Exception.ErrorCode;
import kr.co.polycube.backendtest.Exception.ResourceNotFoundException;
import kr.co.polycube.backendtest.Repository.UserRepository;
import kr.co.polycube.backendtest.Service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> 
				new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "없는 아이디 입니다. : " + id));
	}

	@Override
	public User updateUser(Long id, UserDTO userDTO) {
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> 
				new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + id));
		
		user.setName(userDTO.getName());
		
        return userRepository.save(user);
		
	}

}
