package ro.fmi.ip.trei.coffeetracker.entry;

import java.util.Date;

public class LoginState {

    private String otpVerificationId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public LoginState() {

    }

    private LoginState(String otpVerificationId, String phoneNumber,
                       String firstName, String lastName, Date birthDate) {
        this.otpVerificationId = otpVerificationId;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getOtpVerificationId() {
        return otpVerificationId;
    }

    public void setOtpVerificationId(String otpVerificationId) {
        this.otpVerificationId = otpVerificationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public static class Builder {
        private String otpVerificationId;
        private String phoneNumber;
        private String firstName;
        private String lastName;
        private Date birthDate;



        public Builder otpVerificationId(String otpVerificationId) {
            this.otpVerificationId = otpVerificationId;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }



        public LoginState build() {
            return new LoginState(otpVerificationId, phoneNumber,
                    firstName, lastName, birthDate);
        }
    }
}
