package com.example.servicemarket.model;

public class LoginResponse<T> {
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public LoginResponse.response getResponse() {
        return response;
    }

    public void setResponse(LoginResponse.response response) {
        this.response = response;
    }

    private String responseCode, responseMessage;

    private response response;

    public LoginResponse(String responseCode, String responseMessage, response response){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.response = response;
    }

    public class response
    {
        private String name;
        private userToken userToken;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LoginResponse.response.userToken getUserToken() {
            return userToken;
        }

        public void setUserToken(LoginResponse.response.userToken userToken) {
            this.userToken = userToken;
        }

        public LoginResponse.response.userDetails getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(LoginResponse.response.userDetails userDetails) {
            this.userDetails = userDetails;
        }

        private userDetails userDetails;

        public class userToken{
            private String token;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }

            private String validity;

        }
        public class userDetails{
            private String dateOfBirth;
            private String nicNumber;
            private String nicExpiryDate;
            private String city;
            private String country;
            private String lastLogin;

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
            }

            public String getNicNumber() {
                return nicNumber;
            }

            public void setNicNumber(String nicNumber) {
                this.nicNumber = nicNumber;
            }

            public String getNicExpiryDate() {
                return nicExpiryDate;
            }

            public void setNicExpiryDate(String nicExpiryDate) {
                this.nicExpiryDate = nicExpiryDate;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLastLogin() {
                return lastLogin;
            }

            public void setLastLogin(String lastLogin) {
                this.lastLogin = lastLogin;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            private String email;
        }
    }
}
