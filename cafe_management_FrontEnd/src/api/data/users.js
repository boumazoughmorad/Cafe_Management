export const createUser = (user) => {
    const {  email, firstName, id,  lastName, password } =
      user;
  
    return {
      id,
      email,
      firstName,
      lastName,
      password,
      createdAt: new Date(),
      modifiedAt: new Date(),
    };
  };
  
  export const usersData = [
   {
      id: 1,
      email: 'morad@boumazough.com',
      firstName: 'morad',
      lastName: 'boumazough',
      password: '123456789',
    },
  ];
  