import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.repository.QuestionRepository;
import com.wipro.doconnect.repository.UserRepository;
import com.wipro.doconnect.service.QuestionServiceImpl;

class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setName("Srikar");

        question = new Question();
        question.setQuestionId(1L);
        question.setTitle("Test Title");
        question.setDescription("Test Desc");
        question.setUser(user);
    }
    
    @Test
    void testSaveQuestion() {

        QuestionDto dto = new QuestionDto();
        dto.setTitle("Test Title");
        dto.setDescription("Test Desc");
        dto.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question saved = questionService.saveQuestion(dto);

        assertNotNull(saved);
        assertEquals("Test Title", saved.getTitle());
    }
    
    @Test
    void testGetAllQuestions() {

        when(questionRepository.findAll()).thenReturn(List.of(question));

        List<Question> list = questionService.getAllQuestions();

        assertEquals(1, list.size());
    }
    
    @Test
    void testGetQuestionById() {

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        Question result = questionService.getQuestionById(1L);

        assertEquals("Test Title", result.getTitle());
    }
    
    @Test
    void testUpdateQuestion() {

        QuestionDto dto = new QuestionDto();
        dto.setTitle("Updated Title");
        dto.setDescription("Updated Desc");
        dto.setUserId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question updated = questionService.updateQuestion(1L, dto);

        assertEquals("Updated Title", updated.getTitle());
    }
    
    @Test
    void testDeleteQuestion() {

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        questionService.deleteQuestion(1L);

        verify(questionRepository, times(1)).delete(question);
    }
    
    @Test
    void testGetQuestionById_NotFound() {

        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            questionService.getQuestionById(1L);
        });
    }
    
}
