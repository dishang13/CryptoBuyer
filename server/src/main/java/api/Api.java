package api;

import business.CryptoCurrency;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@ApplicationPath("/")
@Path("/")
public class Api {

    @GET
    @Path("details")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<String,CryptoCurrency>> details(@Context HttpServletRequest httpRequest) {
        try {

            Map<String, Map<String,CryptoCurrency>> currencies = CryptoCurrency.getPrices();
            return currencies;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO
//            throw new ApiException("categories lookup failed", e);
        }
        return null;
    }
}
