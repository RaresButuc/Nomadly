import EmailInput from "../components/form-components/Email-input";
import UsernameInput from "../components/form-components/Username-input";
import PasswordInput from "../components/form-components/Password-input";
import PhoneNumberInput from "../components/form-components/PhoneNumber-input";

export default function RegisterPage() {
  return (
    <>
      <form
      //   onSubmit={onSave}
      >
        <div className="container py-2 mt-4 ">
          <div className="row vh-100 d-flex justify-content-center align-items-center ">
            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
              <div className="border border-warning rounded">
                <div
                  className="card-body p-4 text-center"
                  style={{ backgroundColor: "rgba(255, 255, 255,0.5)" }}
                >
                  <h1 className="mb-4">Register</h1>
                  <hr style={{ color: "#ffca2b" }} />

                  <div className="form-outline mb-4 mt-5">
                    <UsernameInput id={"floatingTitleValue"} />
                  </div>

                  <div className="form-outline mb-4">
                    <EmailInput
                      user={null}
                      ref={null}
                      id={"floatingEmailValue"}
                    />
                  </div>

                  <div className="form-outline mb-4">
                    <PasswordInput id={"floatingPasswordValue"} />
                  </div>

                  <div className="form-outline mb-4">
                    <PhoneNumberInput id={"floatingPhoneNumberValue"} />
                  </div>

                  {/*<div className="form-outline mb-4 mt-5">
                    <CountrySelect
                      user={null}
                      ref={null}
                      id={"floatingCountryValue"}
                    />
                  </div>

                  <div className="form-outline mb-4 mt-5">
                    <ShortDescriptionInput
                      user={null}
                      ref={null}
                      id={"floatingShortDescriptionValue"}
                    />
                  </div>

                  <div className="form-outline mb-5 mt-5">
                    <h4>Profile Picture</h4>
                    <ProfileImageInput ref={photoRef} />
                  </div>

                  <div className="form-outline mb-5 mt-5">
                    <h4>Background Picture</h4>
                    <ProfileBackgroundImageInput ref={photoBackgroundRef} />
                  </div>

                  <div className="form-outline mb-5 mt-5">
                    <h4>Social Media</h4>
                    <SocialMediaInput ref={instagram} platform={"instagram"} />
                    <SocialMediaInput ref={facebook} platform={"facebook"} />
                    <SocialMediaInput ref={x} platform={"x"} />
                  </div>*/}

                  <button
                    className="btn btn-primary btn-lg btn-block"
                    type="submit"
                  >
                    Register
                  </button>
                </div>
              </div>
              <p className="mt-4 d-flex justify-content-center">
                Already a member?
                <a href="/login" className="text-warning">
                  Log In HERE
                </a>
              </p>
            </div>
          </div>
        </div>
      </form>
    </>
  );
}
