"use client";

import { useState } from "react";

export default function PasswordInput({ id }) {
  const [visible, setVisible] = useState(false);

  return (
    <div className="form-floating">
      <input
        className="form-control"
        type={visible ? "text" : "password"}
        name="passwordInput"
        id={id}
        placeholder="Password"
        required
      />
      <label htmlFor={id}>Password</label>
      <div>
        <input
          type="checkbox"
          className="me-2 my-3"
          checked={visible}
          onChange={() => setVisible(!visible)}
        />
        <label>Show Password</label>
      </div>
    </div>
  );
}
