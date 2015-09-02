package mct.androtech.mrhsce.mct;

/**
 * Created by mrhs on 9/1/15.
 */
public interface Commons {
    public final String farhadNum = "09393921260";
    // shows that the app is executing calling task and another calling task can not be executed
    // when a task starts this will be set to true and at the end it will be false
    public Boolean IS_CALLING = false;
    // If true when the app is executing MC task all the incoming calls will be rejected automatically
    public Boolean REJECT_INCOMING_CALLS = false;
    public final Boolean SHOW_LOG = true;
}
