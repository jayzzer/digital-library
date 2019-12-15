package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
