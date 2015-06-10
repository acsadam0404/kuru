package hu.kuru.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.helper.resize.PluploadImageResize;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class ArticleImageUploaderComp extends CustomComponent {
	
	private String imageName;

	public ArticleImageUploaderComp() {
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout uplComp = new VerticalLayout();
		uplComp.setSizeFull();
		HorizontalLayout uplPackComp = new HorizontalLayout();
		uplPackComp.setSizeFull();

		final Plupload uploader = new Plupload("Tallózás", FontAwesome.FILES_O);
		uploader.setCaption("Kép feltöltése");
		final Label info = new Label();
		uploader.setMaxFileSize("5mb");
		uploader.setImageResize(new PluploadImageResize().setEnabled(true)
				.setCrop(false).setWidth(128).setHeight(128));

		uploader.addFileUploadedListener(new Plupload.FileUploadedListener() {
			@Override
			public void onFileUploaded(PluploadFile file) {
				try {
					File filed = (File) file.getUploadedFile();
					ArticleImageUploaderComp.this.imageName = UUID.randomUUID().toString();
					Path uploaded = filed.toPath();
					Path root = Paths.get(KuruUtils
							.getArticlePicturePathFromFileSystem());
					Files.createDirectories(root);
					Path path = root.resolve(file.getName());
					Files.copy(uploaded, path,
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		uploader.addUploadProgressListener(new Plupload.UploadProgressListener() {
			@Override
			public void onUploadProgress(PluploadFile file) {
				info.setValue("Feltöltés folyamatban:" + file.getPercent()
						+ "%");
			}
		});

		uploader.addFilesAddedListener(new Plupload.FilesAddedListener() {
			@Override
			public void onFilesAdded(PluploadFile[] files) {
				uploader.start();
			}
		});

		uploader.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
			@Override
			public void onUploadComplete() {
				info.setValue("A feltöltés befejeződött!");
			}
		});

		uploader.addErrorListener(new Plupload.ErrorListener() {
			@Override
			public void onError(PluploadError error) {
				Notification.show("Hiba történt a feltöltés során!",
						Notification.Type.ERROR_MESSAGE);
			}

		});

		uplPackComp.addComponent(uploader);
		uplComp.addComponent(uplPackComp);
		uplComp.addComponent(info);
		return uplComp;
	}
	
	public String getImageName() {
		return imageName;
	}
}
