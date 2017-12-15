package at.hacker.shorten;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ShortenRepository extends JpaRepository<ShortenedUrl, Long> {

}
