package kr.co.polycube.backendtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.polycube.backendtest.Entity.Winner;

public interface WinnerRepository extends JpaRepository<Winner, Long> {

}
