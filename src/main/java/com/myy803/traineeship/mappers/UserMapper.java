package com.myy803.traineeship.mappers;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myy803.traineeship.domainmodel.User;


public interface UserMapper extends JpaRepository<User, Integer> { //παιρνω τα δεδομενα απτο αντικειμενο user και το int ειναι το id tou
															//ΣΕ ΟΛΑ ΤΑ ΜΑΠΕΡΣ ΕΚΤΟΣ ΑΠΤΗΣ ΕΤΑΙΡΕΙΑΣ kai tou student ΕΙΝΑΙ ΤO ΙΔΙΟ ΠΡΑΓΜΑ
	Optional<User> findByUsername(String username);			//ΣΤΗΝ ΕΤΑΙΡΕΙΑ(kaisto student) ΑΝΤΙ ΓΙΑ INTEGER ΕΧΩ ΣΤΡΙΝΓΚ ΑΦΟΥ ΤΟ ID ΜΟΥ ΕΙΝΑΙ ΤΟ ΟΝΟΜΑ ΤΗΣ ΕΤΑΙΡΕΙΑΣ
}
