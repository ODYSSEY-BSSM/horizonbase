package odyssey.backend.domain.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.exception.NotLeaderException;
import odyssey.backend.presentation.team.dto.request.TeamRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User leader;

    @ManyToMany
    private List<User> members = new ArrayList<>();

    Team(String name, User leader){
        this.name = name;
        this.leader = leader;
        members.add(leader);
    }

    public static Team ok(TeamRequest request, User leader){
        return new Team(
                request.getName(),
                leader
        );
    }

    public void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    public boolean isLeader(User user){
        return this.leader.getUuid().equals(user.getUuid());
    }

    public void validateLeader(User user){
        if(!isLeader(user)){
            throw new NotLeaderException();
        }
    }

    public String getLeaderUsername(){
        return this.leader.getUsername();
    }

}
