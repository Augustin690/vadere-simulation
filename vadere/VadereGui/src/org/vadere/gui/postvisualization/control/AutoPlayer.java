package org.vadere.gui.postvisualization.control;


import org.vadere.gui.postvisualization.model.PostvisualizationModel;
import org.vadere.gui.postvisualization.utils.MovRecorder;
import org.vadere.util.logging.Logger;

import java.io.File;
import java.io.IOException;

public class AutoPlayer extends Player {
	private static Logger logger = Logger.getLogger(AutoPlayer.class);
	private static volatile AutoPlayer instance;
	private final MovRecorder movRecorder;

	private  boolean isRestart = true;

	private File outputFile;

	private PostvisualizationModel model;

	public static AutoPlayer getInstance(final PostvisualizationModel model, MovRecorder movRecorder, File outputFile) {
		if (instance == null) {
			instance = new AutoPlayer(model, movRecorder, outputFile);
		}
		instance.model = model;
		return instance;
	}


	private AutoPlayer(final PostvisualizationModel model, final MovRecorder movRecorder, File outputFile) {
		super(model);
		this.movRecorder = movRecorder;
		this.outputFile = outputFile;
	}



	public void start(){

		try {
			this.movRecorder.startRecording(outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (!isRunning()) {
			setRunning(true);
		}


	}


	@Override
	public void run() {

		while (isRunning() && isRunnable()) {
			long ms = System.currentTimeMillis();

			double newSimeTimeInSec = model.getSimTimeInSec() + model.getTimeResolution();

			if (( model.getSimTimeInSec() + model.getTimeResolution() ) >= model.getMaxSimTimeInSec() ) {
				try {
					movRecorder.stopRecording();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				setRunning(false);
			} else {

				model.setVisTime(newSimeTimeInSec);
				model.notifyObservers();
			}
			sleep(ms);
		}

	}

}
