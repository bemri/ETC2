package emribalazs.hu.etc;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SolutionDialog extends DialogFragment {
    Button okButton, cancelButton;
    TextView questionText;
    EditText answerText;
    SolutionMapCommunicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.solution_dialog, null);

        okButton = view.findViewById(R.id.solutionButtonOK);
        cancelButton = view.findViewById(R.id.solutionButtonCancel);
        questionText = view.findViewById(R.id.questionText);
        answerText = view.findViewById(R.id.answerText);

        okButton.setOnClickListener(new SolutionOKButtonClick());
        cancelButton.setOnClickListener(new SolutionCancelButtonClick());

        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            questionText.setText(getArguments().getString("question"));
        }

        return view;
    }

    class SolutionOKButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            communicator.answerReceived(answerText.getText().toString());
            dismiss();
        }
    }

    class SolutionCancelButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    }

    @Override
    public void onResume() {
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
        super.onResume();
    }

    interface SolutionMapCommunicator {
        void answerReceived(String answer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (SolutionMapCommunicator) activity;
    }
}

