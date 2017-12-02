package com.defense.repository;

import com.defense.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepo extends JpaRepository<Setting, Long> {
}
