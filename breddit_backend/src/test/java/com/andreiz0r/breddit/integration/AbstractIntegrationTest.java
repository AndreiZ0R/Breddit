package com.andreiz0r.breddit.integration;

import com.andreiz0r.breddit.utils.AppHttpMethod;
import com.andreiz0r.breddit.utils.AppUtils;
import com.andreiz0r.breddit.utils.TestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    @LocalServerPort
    private int port;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * <b>Asserts that a given method on a given route returns a success response with the given payload.</b>
     * Utility function for rest controllers.
     *
     * @param method       <i>String</i>: HTTP Method to test: "GET", "POST", "PUT", "DELETE"...
     * @param endPoint     <i>String</i>: Endpoint to test. Eg: <i>/api/test</i>
     * @param expectedBody <i>Object</i>: Response expectedBody. Eg: <i>new User("Andrei",25)</i>
     */
    public void assertThatRespondedWithSuccess(final AppHttpMethod method, final String endPoint, final Object requestBody, final Object expectedBody) {
        LOG.debug("Performing {}: {} with expectedBody {}", method, endPoint, expectedBody);
        try {
            mockMvc.perform(
                            getHttpMethod(method, TestUtils.buildTestUrl(endPoint, port))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(TestUtils.toJsonString(requestBody)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(TestUtils.toJsonString(expectedBody, AppUtils.SUCCESS)));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * <b>Asserts that a given method on a given route returns a failure response with the given error message and status code.</b>
     * Utility function for rest controllers.
     *
     * @param method       <i>String</i>: HTTP Method to test: "GET", "POST", "PUT", "DELETE"...
     * @param endPoint     <i>String</i>: Endpoint to test. Eg: <i>/api/test</i>
     * @param status       <i>HttpStatus</i>: The status code for the response.
     * @param errorMessage <i>String</i>: The correlated error message.
     */
    public void assertThatRespondedWithFailure(final AppHttpMethod method, final String endPoint, final HttpStatus status, final String errorMessage) {
        LOG.debug("Performing {}: {} with expected error message {}", method, endPoint, errorMessage);

        try {
            mockMvc.perform(getHttpMethod(method, TestUtils.buildTestUrl(endPoint, port)))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(content().json(TestUtils.toJsonString(errorMessage)));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private MockHttpServletRequestBuilder getHttpMethod(final AppHttpMethod method, final String URL) {
        return switch (method) {
            case GET -> get(URL);
            case PUT -> put(URL);
            case POST -> post(URL);
            case DELETE -> delete(URL);
        };
    }
}
