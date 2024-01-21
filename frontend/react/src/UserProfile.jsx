const UserProfile=({name,age,gender,imageNumber, ...props})=>{

     gender= gender==="MALE"?"men":"women"
   return (
       <div>
           <h1>{name}</h1>
           <h1>{age}</h1>
           <img src={`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}
           />
           {props.children}
       </div>
   )
}

export default UserProfile;