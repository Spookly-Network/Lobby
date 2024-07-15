package de.spookly.lobby.factory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class PlayerCosmeticProfileRecord {

	private String PROFILE_UUID;
	private String SLOT;
	private String COSMETIC_KEY;
	private String COSMETIC_DATA;

}
