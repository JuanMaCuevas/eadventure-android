package es.eucm.eadandroid.homeapp.repository.resourceHandler.progressTracker;

import java.util.Vector;

// FIXME se pueden notificar a cualquier nodo del arbol , 
//deberia solo permitir notificar al ultimo task creado

public class ProgressTask extends TaskNode {

	private String name;
	private String desc;

	protected int selfProgress;
	
	protected int selfPercentage;
	
	protected int childsPercentage;

	private TaskNode parent;


	public ProgressTask(ProgressNotifier pn, String name, String desc) {

		init(pn, name, desc);

	}

	private ProgressTask(ProgressTask parent, String name, String desc) {

		init(parent, name, desc);

	}

	private void init(TaskNode parent, String name, String desc) {

		this.parent = parent;
		this.name = name;
		this.desc = desc;
		this.selfProgress = 0;
		this.selfPercentage = MAX_PROGRESS_VALUE;
		this.childsPercentage = 0;


	}

	public ProgressTask createChildTask(String name, String desc,
			int newChildPercentage) {

		ProgressTask child = null;

		try {
			if (selfProgress + this.childsPercentage+newChildPercentage > MAX_PROGRESS_VALUE) {
				throw new IncosistentTaskException(
						"Totaling percentages exceeds "
								+ MAX_PROGRESS_VALUE
								+ ". Parent progress plus child percentages add up more than "
								+ MAX_PROGRESS_VALUE);
			}

			else
				child = new ProgressTask(this, name, desc);
				this.childsPercentage+=newChildPercentage;

		} catch (IncosistentTaskException e) {
			e.printStackTrace();
		}

		return child;

	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public void notifyProgress(int progress, String operationMsg) {

		try {
			if (progress > MAX_PROGRESS_VALUE) {
				throw new IncosistentTaskException(
						"Progress value can't be greater than "
								+ MAX_PROGRESS_VALUE);
			} else {

				Float auxPercentage = new Float(progress) / MAX_PROGRESS_VALUE
						* this.selfPercentage;

				this.selfProgress = auxPercentage.intValue();

				parent.notifyProgress(this.selfProgress, operationMsg);

			}

		} catch (IncosistentTaskException e) {
			e.printStackTrace();
		}
	}

	public void notifyFinished(String finishedMsg) {

		// FIXME esto no tira hay que tratar el finished con la clase abstracta
		// ;D!

	/*	this.selfProgress += this.childPercentage;

		if (this.selfProgress == MAX_PROGRESS_VALUE)
			parent.notifyFinished(finishedMsg);

		else
			parent.notifyProgress(this.selfProgress, finishedMsg);*/

	}

	public void notifyError(String error) {

		parent.notifyError(error);

	}

}
