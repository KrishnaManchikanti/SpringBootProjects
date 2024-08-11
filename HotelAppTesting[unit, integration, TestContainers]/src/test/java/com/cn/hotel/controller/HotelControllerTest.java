package com.cn.hotel.controller;


import com.cn.hotel.jwt.JwtAuthenticationHelper;
import com.cn.hotel.model.Hotel;
import com.cn.hotel.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing Controller Layer
 */
@WebMvcTest(controllers = HotelController.class)
public class HotelControllerTest {

    @MockBean
    private HotelService hotelService;

    @MockBean
    private JwtAuthenticationHelper jwtAuthenticationHelper;

    @Autowired
    private MockMvc mockMvc;

    /**
     * WithMockUser Annotation we can test based on Roles
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldTestGetAllHotels() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Raddison Hotel");
        hotel.setRating(3L);
        when(hotelService.getAllHotels())
                .thenReturn(List.of(hotel));

        mockMvc.perform(get("/hotel/getAll"))
                .andExpect(status().is(200));

    }
}
