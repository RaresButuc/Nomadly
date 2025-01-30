export default function EmailInput({ user, id }) {
    return (
      <div className="form-floating">
        <input
          className="form-control"
          type="email"
          name="emailInput"
          id={id}
          placeholder="UserName"
          defaultValue={user?.email}
          required
        />
        <label htmlFor={id}>Email</label>
      </div>
    );
  }
  