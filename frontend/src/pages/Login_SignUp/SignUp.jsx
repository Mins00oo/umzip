import SignUpForm from "../../components/Login_SignUp/SignUpForm";
import classes from './SignUp.module.css';
export default function SignUp() {
    return (<>
    <div   className={`${classes.backgroundContainer}`}>
    <SignUpForm/>

    </div>
   
    </>)
}