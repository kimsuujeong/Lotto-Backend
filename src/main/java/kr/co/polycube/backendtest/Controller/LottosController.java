package kr.co.polycube.backendtest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.polycube.backendtest.DTO.LottosNumbersDTO;
import kr.co.polycube.backendtest.Entity.Lotto;
import kr.co.polycube.backendtest.Service.LottosService;

@RestController
@RequestMapping("/lottos")
public class LottosController {
	
	@Autowired
	LottosService lottosService;
	
	@PostMapping
	public ResponseEntity<LottosNumbersDTO> generateLotto() {
        Lotto lotto = lottosService.generateLottoNumbers();
        
        LottosNumbersDTO lottosDTO = new LottosNumbersDTO();
        lottosDTO.setNumbers(
        		new int[] 
        		{
                lotto.getNumber_1(),
                lotto.getNumber_2(),
                lotto.getNumber_3(),
                lotto.getNumber_4(),
                lotto.getNumber_5(),
                lotto.getNumber_6()
                });
        
        return ResponseEntity.ok(lottosDTO);
    }

}
