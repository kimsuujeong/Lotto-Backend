package kr.co.polycube.backendtest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.polycube.backendtest.DTO.UserDTO;
import kr.co.polycube.backendtest.Entity.User;
import kr.co.polycube.backendtest.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user) {

		User createUser = userService.saveUser(user);

		return ResponseEntity.ok().body("{\"id\": " + createUser.getId() + "}");

	}
	
	@GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
		
        User user = userService.getUserById(id);
        
        UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
        
        return ResponseEntity.ok(userDTO);
    }

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, 
											  @RequestBody UserDTO userDTO) {

        User updatedUser = userService.updateUser(id, userDTO);

        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(updatedUser.getId());
        updatedUserDTO.setName(updatedUser.getName());
        
        return ResponseEntity.ok(updatedUserDTO);

	}

//	- [ ] 필터 구현 (12점)
//	  - [ ] URL에 `? & = : //`를 제외한 특수문자가 포함되어 있을경우 접속을 차단하는 Filter 구현한다.
//	  - [ ] `/users/{id}?name=test!!` API 호출에 대한 통합 테스트 코드 작성
//	- [ ] Spring AOP를 활용한 로깅 구현 (14점)
//	  - [ ] user 등록, 조회, 수정 API에 대해 Request시 Console에 Client Agent를 출력한다

}
