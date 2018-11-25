package pl.sdacademy.chat.model;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Java6Assertions.assertThat;


class DatedChatMessageTest {
    @Test
    public void shouldCorrectCreateDataChatMessageClass() {
        // Given
        DatedChatMessage datedChatMessage = new DatedChatMessage(new ChatMessage("Radek", "Hello"));
        // When
        String stringToCompare = datedChatMessage.getAuthor();
        String message = datedChatMessage.getMessage();
        LocalDateTime reciveDate = datedChatMessage.getReciveDate();
        // Then
        assertThat(stringToCompare).isEqualTo("Radek");
        assertThat(message).isEqualTo("Hello");
        assertThat(reciveDate).isNotNull();
    }


}