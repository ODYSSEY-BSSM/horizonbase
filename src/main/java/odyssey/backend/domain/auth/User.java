package odyssey.backend.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.team.Team;
import odyssey.backend.presentation.user.dto.request.SignUpRequest;

import java.util.List;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Team> teams;

    private String school;

    private Boolean isConnectedSchool;

    public static User from(SignUpRequest request, String password, Role role) {
        return new User(request.getEmail(), request.getUsername(), password, role);
    }

    public User(String email, String username, String password, Role role){
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.school = "";
        this.isConnectedSchool = false;
    }

    public void connectSchool(){
        this.school = "부산소프트웨어마이스터고등학교";
        this.isConnectedSchool = true;
    }

    public void updatePassword(String password){
        this.password = password;
    }

}


