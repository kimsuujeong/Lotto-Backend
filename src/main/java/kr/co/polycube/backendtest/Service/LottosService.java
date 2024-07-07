package kr.co.polycube.backendtest.Service;

import java.util.List;

import kr.co.polycube.backendtest.Entity.Lotto;

public interface LottosService{

	Lotto generateLottoNumbers();

	List<Lotto> getAllLottos();

	void checkWinners(List<Lotto> lottos, int[] winningNumbers, int bonusNumber);

}
