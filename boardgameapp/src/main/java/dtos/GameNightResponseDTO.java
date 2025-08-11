package dtos;

import java.time.LocalDateTime;
import java.util.List;
import com.iu.gameboardapp.model.enums.GameNightStatus;

public record GameNightResponseDTO (
    Long id,
    LocalDateTime date,
    String location,
    GameNightStatus status,
    Integer maxParticipants,
    Long hostId,
    List<Long> participantIds
    )
{}

//
//    public long getId() { return id; }
//
//    public void setId(long id) { this.id = id; }
//
//    public LocalDateTime getDate() { return date; }
//
//    public void setDate(LocalDateTime date) { this.date = date; }
//
//    public String getLocation() { return location; }
//
//    public void setLocation(String location) { this.location = location; }
//
//    public GameNightStatus getStatus() { return status; }
//
//    public void setStatus(GameNightStatus status) { this.status = status; }
//
//    public int getMaxParticipants() { return maxParticipants; }
//    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
//
//    public Long getHostId() { return hostId; }
//    public void setHostId(Long hostId) { this.hostId = hostId; }
//
//
//    public List<Long> getParticipantIds() { return participantIds; }
//
//    public void setParticipantIds(List<Long> participantIds) { this.participantIds = participantIds; }
