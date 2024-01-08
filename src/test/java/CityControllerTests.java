import com.example.springapiweather.City;
import com.example.springapiweather.controllers.CityController;
import com.example.springapiweather.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ContextConfiguration(classes = {CityController.class})
@WebMvcTest(CityController.class)
public class CityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityRepository cityRepository;

    @InjectMocks
    private CityController cityController;

    @Test
    public void testReadCity() throws Exception {
        when(cityRepository.findById(1)).thenReturn(Optional.of(new City("TestCity", "TestWeather")));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cities/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":null,\"name\":\"TestCity\",\"weather\":\"TestWeather\"}"));
    }

    @Test
    public void testCreateCity() throws Exception {
        when(cityRepository.save(any())).thenReturn(new City("TestCity", "TestWeather"));

        String requestBody = "{\"name\":\"TestCity\",\"weather\":\"TestWeather\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cities/")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestCity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weather").value("TestWeather"));
    }

    @Test
    public void testUpdateCity() throws Exception {
        when(cityRepository.findById(1)).thenReturn(Optional.of(new City("TestCity", "TestWeather")));
        when(cityRepository.save(any())).thenReturn(new City("TestCity", "TestWeather"));

        String requestBody = "{\"name\":\"TestCity\",\"weather\":\"TestWeather\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cities/update/1")
                        .param("name", "TestCity")
                        .param("weather", "TestWeather")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Updated"));
    }

    @Test
    public void testDeleteCity() throws Exception {
        when(cityRepository.findById(1)).thenReturn(Optional.of(new City("TestCity", "TestWeather")));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cities/deleteCity/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted City with ID: 1"));
    }

}
