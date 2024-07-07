package kr.co.polycube.backendtest.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.Entity.Lotto;
import kr.co.polycube.backendtest.Entity.Winner;
import kr.co.polycube.backendtest.Repository.LottosRepository;
import kr.co.polycube.backendtest.Repository.WinnerRepository;
import kr.co.polycube.backendtest.Service.LottosService;

@Service
public class LottosServiceImpl implements LottosService {
	
	@Autowired
	private LottosRepository lottosRepository;
	
	@Autowired
	private WinnerRepository winnerRepository;
	
	private final Random random = new Random();

	@Override
	public Lotto generateLottoNumbers() {
		Set<Integer> numbers = generateUniqueRandomNumbers();
        return lottosRepository.save(convertToLottoEntity(numbers));
	}
	
	private Set<Integer> generateUniqueRandomNumbers() {
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < 6) {
            int number = random.nextInt(45) + 1; // 1부터 45까지의 난수 생성
            numbers.add(number);
        }
        return numbers;
    }

    private Lotto convertToLottoEntity(Set<Integer> numbers) {
        Lotto lotto = new Lotto();
        Integer[] numArray = numbers.toArray(new Integer[0]);
        
        lotto.setNumber_1(numArray[0]);
        lotto.setNumber_2(numArray[1]);
        lotto.setNumber_3(numArray[2]);
        lotto.setNumber_4(numArray[3]);
        lotto.setNumber_5(numArray[4]);
        lotto.setNumber_6(numArray[5]);
        
        return lotto;
    }
    
    @Override
	public void checkWinners(List<Lotto> lottos, int[] winningNumbers, int bonusNumber) {
    	for (Lotto lotto : lottos) {
    		
            int matchCount = (int) IntStream.of(
            		
                    lotto.getNumber_1(),
                    lotto.getNumber_2(),
                    lotto.getNumber_3(),
                    lotto.getNumber_4(),
                    lotto.getNumber_5(),
                    lotto.getNumber_6()
                    
            ).filter(num -> IntStream.of(winningNumbers).anyMatch(w -> w == num)).count();

            int rank = determineRank(matchCount, bonusNumber, lotto);
            if (rank > 0) {
                Winner winner = new Winner();
                winner.setLottos_id(lotto.getId());
                winner.setRank(rank+"등");
                winnerRepository.save(winner);
            }
        }
		
	}
    
    private int determineRank(int matchCount, int bonusNumber, Lotto lotto) {
        if (matchCount == 6) {
            return 1;
        } else if (matchCount == 5 && hasBonusNumber(lotto, bonusNumber)) {
            return 2;
        } else if (matchCount == 5) {
            return 3;
        } else if (matchCount == 4) {
            return 4;
        } else if (matchCount == 3) {
            return 5;
        }
        return 0;
    }
    
    private boolean hasBonusNumber(Lotto lotto, int bonusNumber) {
        return IntStream.of(
                lotto.getNumber_1(),
                lotto.getNumber_2(),
                lotto.getNumber_3(),
                lotto.getNumber_4(),
                lotto.getNumber_5(),
                lotto.getNumber_6()
        ).anyMatch(num -> num == bonusNumber);
    }

	@Override
	public List<Lotto> getAllLottos() {
		return lottosRepository.findAll();
	}
	
}
