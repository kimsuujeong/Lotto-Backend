package kr.co.polycube.backendtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.polycube.backendtest.Entity.Lotto;

@Repository
public interface LottosRepository extends JpaRepository<Lotto, Long>{

}
