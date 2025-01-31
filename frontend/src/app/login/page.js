"use client";

import axios from "axios";
import { useState } from "react";
import { useRouter } from "next/navigation";
import DefaultURL from "../usefull/DefaulURL";
import useSignIn from "react-auth-kit/hooks/useSignIn";

import EmailInput from "../components/form-components/Email-input";
import PasswordInput from "../components/form-components/Password-input";

export default function LoginPage() {
  const signIn = useSignIn();
  const router = useRouter();

  const [showAlert, setShowAlert] = useState(false);
  const [alertInfos, setAlertInfos] = useState(["", "", ""]);

  const onSubmit = async (values) => {
    try {
      const response = await axios.post(
        `${DefaultURL}/user/authenticate`,
        values
      );

      signIn({
        token: response.data.token,
        expiresIn: 3600,
        tokenType: "Bearer",
        authState: { email: values.email, role: response.data.role },
      });

      router.push(document.referrer.slice(21));
    } catch (err) {
      setShowAlert(true);
      setAlertInfos([
        "Be Careful!",
        "Wrong Email or Password! Try Again!",
        "danger",
      ]);
      setTimeout(() => {
        setShowAlert(false);
      }, 3000);
    }
  };

  const onSave = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const authenticateData = {
      email: formData.get("emailInput"),
      password: formData.get("passwordInput"),
    };
    onSubmit(authenticateData);
  };

  return (
    <>
      {showAlert ? (
        <Alert
          type={alertInfos[0]}
          message={alertInfos[1]}
          color={alertInfos[2]}
        />
      ) : null}

      <form onSubmit={onSave}>
        <div className="container py-2 mt-4 ">
          <div className="row vh-100 d-flex justify-content-center align-items-center ">
            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
              <div className="border border-warning rounded">
                <div
                  className="card-body p-4 text-center"
                  style={{ backgroundColor: "rgba(255, 255, 255,0.5)" }}
                >
                  <h1 className="mb-4">Log In</h1>
                  <hr style={{ color: "#ffca2b" }} />

                  <div className="form-outline mt-4 mb-4">
                    <EmailInput
                      user={null}
                      ref={null}
                      id={"floatingEmailValue"}
                    />
                  </div>

                  <div className="form-outline mb-4">
                    <PasswordInput id={"floatingPasswordValue"} />
                  </div>

                  <button
                    className="btn btn-primary btn-lg btn-block"
                    type="submit"
                  >
                    Register
                  </button>
                </div>
              </div>
              <p className="mt-4 d-flex justify-content-center">
                Not a member yet?
                <a href="/login" className="text-warning">
                  Register HERE
                </a>
              </p>
            </div>
          </div>
        </div>
      </form>
    </>
  );
}
