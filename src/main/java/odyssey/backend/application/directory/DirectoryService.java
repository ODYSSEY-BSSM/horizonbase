package odyssey.backend.application.directory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.directory.Directory;
import odyssey.backend.domain.directory.exception.DirectoryNotFoundException;
import odyssey.backend.infrastructure.persistence.directory.DirectoryRepository;
import odyssey.backend.presentation.directory.dto.request.DirectoryRequest;
import odyssey.backend.presentation.directory.dto.response.DirectoryInfoResponse;
import odyssey.backend.presentation.directory.dto.response.DirectoryResponse;
import odyssey.backend.presentation.directory.dto.response.TeamDirectoryResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public DirectoryResponse createDirectory(DirectoryRequest directoryRequest, User user) {
        Directory parent = findParent(directoryRequest.getParentId());

        Directory directory = Directory.from(directoryRequest, parent, user);

        directoryRepository.save(directory);

        return DirectoryResponse.from(directory);
    }

    @Transactional
    public DirectoryResponse updateDirectory(Long id, DirectoryRequest request) {
        Directory directory = findDirectoryById(id);

        Directory parent = findDirectoryById(directory.getId());

        directory.update(request.getName(), parent);

        return DirectoryResponse.from(directory);
    }

    public void deleteDirectory(Long id) {
        Directory directory = findDirectoryById(id);

        directoryRepository.delete(directory);
    }

    public Directory findDirectoryById(Long id) {
        return directoryRepository.findById(id)
                .orElseThrow(DirectoryNotFoundException::new);
    }

    public TeamDirectoryResponse createTeamDirectory(Long teamId, DirectoryRequest request, User user) {
        Directory parent = findParent(request.getParentId());
        
        Directory directory = Directory.fromTeam(request, parent, teamId);
        directoryRepository.save(directory);
        
        TeamDirectoryResponse response = TeamDirectoryResponse.from(directory);

        messagingTemplate.convertAndSend("/topic/directory/team/" + teamId + "/created", response);
        
        return response;
    }

    @Transactional
    public TeamDirectoryResponse updateTeamDirectory(Long id, Long teamId, DirectoryRequest request, User user) {
        Directory directory = findDirectoryById(id);
        
        Directory parent = findParent(request.getParentId());
        
        directory.update(request.getName(), parent);
        TeamDirectoryResponse response = TeamDirectoryResponse.from(directory);
        
        messagingTemplate.convertAndSend("/topic/directory/team/" + teamId + "/updated", response);
        
        return response;
    }

    public void deleteTeamDirectory(Long id, Long teamId, User user) {

        messagingTemplate.convertAndSend("/topic/directory/team/" + teamId + "/deleted", id);
        
        directoryRepository.deleteById(id);
    }

    public List<DirectoryInfoResponse> getDirectoryInfos(User user, String sort){
        List<Directory> directories = directoryRepository.findDirectoriesByUser(user);
        if(sort != null){
            switch(sort){
                case "name" -> directories.sort(Comparator.comparing(Directory::getName));
                case "latest" -> directories.sort(Comparator.comparing(Directory::getCreatedAt));
                default -> directories.sort(Comparator.comparing(Directory::getName));
            }
        }
        else{
            directories.sort(Comparator.comparing(Directory::getName));
        }

        return directories
                .stream()
                .map(DirectoryInfoResponse::from)
                .toList();
    }

    private Directory findParent(Long parentId) {
        return parentId == null ? null : findDirectoryById(parentId);
    }

}
