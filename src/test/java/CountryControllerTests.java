import com.example.springapiweather.Country;
import com.example.springapiweather.controllers.CountryController;
import com.example.springapiweather.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CountryController.class})
@SpringJUnitConfig
@WebMvcTest(CountryController.class)
public class CountryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryController countryController;

    @Test
    public void testAddNewCountry() throws Exception {

        when(countryRepository.save(any())).thenReturn(mock(Country.class));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/countries/add")
                        .param("countryName", "TestCountry")
                        .param("cityName", "TestCity")
                        .param("weather", "TestWeather")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Saved country"));


    }

    @Test
    public void testUpdateCountry() throws Exception {

        when(countryRepository.findById(1)).thenReturn(Optional.of(new Country("TestCountry")));


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/countries/update/1")
                        .param("name", "TestCountry")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Updated"));
    }

    @Test
    public void testDeleteCountry() throws Exception {
        when(countryRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/countries/deleteCountry/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Country not found with id: 1"));
    }

    @Test
    public void testGetAllCountries() throws Exception {
        when(countryRepository.findAll()).thenReturn(Collections.singletonList(new Country("TestCountry")));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/countries/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"cities\":[]}]"));
    }
}
