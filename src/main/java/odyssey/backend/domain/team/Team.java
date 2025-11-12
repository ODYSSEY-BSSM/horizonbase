package odyssey.backend.domain.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.exception.FailedCreateInviteCode;
import odyssey.backend.domain.team.exception.InviteCodeMismatchException;
import odyssey.backend.domain.team.exception.NotLeaderException;
import odyssey.backend.presentation.team.dto.request.TeamRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User leader;

    @ManyToMany
    private List<User> members = new ArrayList<>();

    private String inviteCode;

    Team(String name, User leader) {
        this.name = name;
        this.leader = leader;
        members.add(leader);
        this.inviteCode = createInviteCode();
    }

    public static Team ok(TeamRequest request, User leader) {
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

    private String createInviteCode(){
        try {
            int length = 10;
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }
            return sb.toString();
        }catch(Exception e){
            throw new FailedCreateInviteCode();
        }
    }

    public void validateInviteCode(String inviteCode){
        if(!inviteCode.equals(this.inviteCode)){
            throw new InviteCodeMismatchException();
        }
    }

}
