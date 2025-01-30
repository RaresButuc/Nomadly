export default function UsernameInput({ user, id }) {
  return (
    <div className="form-floating">
      <input
        className="form-control"
        type="name"
        name="nameInput"
        id={id}
        placeholder="UserName"
        defaultValue={user?.name}
        required
      />
      <label htmlFor={id}>UserName</label>
    </div>
  );
}
